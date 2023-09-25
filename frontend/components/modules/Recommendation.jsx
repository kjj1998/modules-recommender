import React from 'react'
import Link from 'next/link'

export default function Recommendation({ module }) {
  return (
    <div className='mb-10'>
      <h2 className='mb-3 text-xl font-semibold text-blue-600 mt-1 hover:text-blue-800'>
        <Link href={`/courses/${module.courseCode}`}>
          {module.courseCode} <span className='lowercase'>{module.courseName}</span>
        </Link>
      </h2>
      <p className='text-slate-600 mb-4'>
        {module.faculty} • {module.gradeType} • {module.academicUnits} AU(s)
      </p>
      <p className='text-slate-600 mb-4'>
        {module.courseInformation}
      </p>
    </div>
  )
}
