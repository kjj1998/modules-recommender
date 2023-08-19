import React from 'react'
import Recommendation from '../components/Recommendation'
import { getCourseRecommendations } from '../../lib/getCourseData'


type Props = {
  params: {
    userId: string,
  }
}

export default async function Recommendations({ params: { userId } }: Props) {
  const recommendations = await getCourseRecommendations('U1920099E')

  return (
    <main className="mx-auto max-w-5xl py-1 min-h-screen mt-16">
      <h2 className='text-xl my-5 font-semibold'>
        Hi John, based on the modules you have taken the following modules are recommended:
      </h2>
      <div>
        <ul>
          <li>
            {
              recommendations?.map((recommendation, index) => 
                <Recommendation key={recommendation.courseCode} course={recommendation} position={index + 1} />
            )}
          </li>
        </ul>
      </div>
    </main>
  )
}