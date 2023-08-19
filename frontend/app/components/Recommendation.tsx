import React from 'react'
import Link from 'next/link'

interface Props {
  course: Course,
  position: number
}

const Recommendation: React.FC<Props> =  ({ course, position }) => {

  return (
    <div className='mb-16'>
      <h2 className='mb-3 text-xl font-semibold text-cyan-600 mt-1'>
        <Link href={`/course/${course.courseCode}`}>
          {course.courseCode} <span className='lowercase'>{course.courseName}</span> (score: {course.score.toPrecision(2)})
        </Link>
      </h2>
      <p className='text-slate-600 mb-4'>
        {course.faculty} • {course.gradeType} • {course.academicUnits} AU(s)
      </p>
      <p className='text-slate-600 mb-4'>
        {course.courseInformation}
      </p>
    </div>
  )
}

export default Recommendation