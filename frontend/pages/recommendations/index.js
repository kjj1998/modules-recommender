import React, { useEffect } from 'react'
import { fetchRecommendations, fetchUserData } from '@/lib/fetchUserData';
import { decode } from 'next-auth/jwt';
import Recommendation from '@/components/modules/Recommendation';
import { useSession, signOut } from 'next-auth/react'
import { useRouter } from 'next/router';
import { isJwtExpired } from 'jwt-check-expiration';


export default function RecommendationsPage(props) {
  const { recommendations, accessToken } = props
  const router = useRouter()
  const { data: session, status } = useSession()

  useEffect(() => {
    if (accessToken === null || accessToken === undefined) {
      router.push('/login')
    } else if (accessToken && isJwtExpired(accessToken)) {
      
    }

  }, [router, accessToken])

  
  if (!recommendations) {
    return <h1 className='mt-20 font-bold text-lg text-center'>Loading ...</h1>
  }

  if (recommendations.http_code === 401) {
    signOut()
    return <h1 className='mt-20 font-bold text-lg text-center'>Session expired! Signing out ...</h1>
  } else {
    const cbfRecommendations = recommendations.data.cbfRecommendations
    const cfRecommendations = recommendations.data.cfRecommendations

    return (
      <main className="mx-auto max-w-5xl py-1 min-h-screen mt-16">
        <div>
          <span className='text-2xl semi-bold'>Recommendations based on modules similar students have taken:</span>
          {
            cfRecommendations.length === 0 ? 
              <div className='my-5'>There are currently no recommendations, please check back later!</div> :
              (
                <ul className='mt-5'>
                  <li>
                    { cfRecommendations.map((recommendation) => <Recommendation key={recommendation.courseCode} module={recommendation} />) }
                  </li>
                </ul>
              )
          }
        </div>
        <div>
          <span className='text-2xl semi-bold'>Recommendations based on the modules you have taken:</span>
          {
            cbfRecommendations.length === 0 ?
              <div className='my-5'>There are currently no recommendations, please add some modules you have taken!</div> :
              (
                <ul className='mt-5'>
                  <li>
                    { cbfRecommendations.map((recommendation) => <Recommendation key={recommendation.courseCode} module={recommendation} />) }
                  </li>
                </ul>
              )
          }
        </div>
      </main>
    )
  }
}

export async function getServerSideProps(context) {

  if (context.req.cookies['next-auth.session-token']) {
    const sessionToken = context.req.cookies['next-auth.session-token'];

    const decodedSessionToken = await decode({
      token: sessionToken,
      secret: process.env.NEXTAUTH_SECRET,
    });

    const userId = decodedSessionToken.userId
    const accessToken = decodedSessionToken.accessToken

    // const user = await fetchUserData(userId, accessToken)
    const recommendations = await fetchRecommendations(userId, accessToken)

    // console.log(user)

    if (!recommendations) {
      return { notFound: true}
    }

    // if (!user) {
    //   return { notFound: true}
    // }

    return {
      props: {
        // user: user,
        recommendations: recommendations,
        accessToken: accessToken
      }
    }
  } else {
    return {
      props: {
        // user: null,
        recommendations: null,
      }
    }
  }
}