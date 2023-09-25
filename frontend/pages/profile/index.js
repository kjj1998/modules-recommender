import React, { useEffect } from 'react'
import { getSession } from 'next-auth/react'
import Profile from '../../components/profile/Profile.jsx'
import { useRouter } from 'next/router';
import { decode } from 'next-auth/jwt';
import { fetchUserData } from '@/lib/fetchUserData.js';
import { fetchAllFaculties } from '@/lib/fetchModuleData.js';

function ProfilePage(props) {
  const { loadedProfile, faculties } = props
  const router = useRouter()

  useEffect(() => {
    getSession().then((session) => {
      if (!session) {
        router.push('/login')
      }
    })
  }, [router])

  if (!loadedProfile) {
    return <h1 className='mt-20 font-bold text-lg text-center'>Loading ...</h1>
  }

  return (
    <Profile profile={loadedProfile} faculties={faculties} />
  )
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

    const student = await fetchUserData(userId, accessToken)

    if (!student) {
      return { notFound: true}
    }

    const faculties = await fetchAllFaculties()

    return {
      props: {
        loadedProfile: student,
        faculties: faculties
      }
    }
  } else {
    return {
      props: {
        loadedProfile: null,
        faculties: null
      }
    }
  }
}

export default ProfilePage