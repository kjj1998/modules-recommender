import React from 'react'
import Link from 'next/link'

type Props = {
  course: Course
}

export default function Item({ course } : Props) {
  return (
    <div className='mb-16'>
      <h2 className='mb-3 text-xl font-semibold text-cyan-600 mt-1'>
        <Link href={`/course/${course.courseCode}`}>
          {course.courseCode} <span className='lowercase'>{course.courseName}</span>
        </Link>
      </h2>
      <p className='text-slate-600 mb-4'>
        {course.faculty} • {course.gradeType} • {course.academicUnits} AU(s)
      </p>
      <p className='text-slate-600 mb-4'>
        {course.courseInformation}
      </p>
      <section className='mb-4'>
        <p className='font-bold'>
          Broadening And Deepening:
        </p>
        <p className='text-slate-600'>
          {course.broadeningAndDeepening === true ? "Yes" : "No"}
        </p>
      </section>
      {/* <section className='mb-4'>
        <p className='font-bold'>
          Mutually Exclusive with:
        </p>
        <p className='text-slate-600'>AB0601, HW0105, HW0106, HW0111, HW0128, HW0188, HW0209</p>
      </section> */}
      {
        course.prerequisites && course.prerequisites.length > 0 ? (
          <section className='mb-4'>
            <p className='font-bold'>
              Prerequisite(s) :
            </p>
            {course.prerequisites.map((prereqGroup, index) => (
              <p key={index} className='text-slate-600'>
                {
                  prereqGroup.map((prereq) => (
                    <span key={prereq} >{prereq} </span>
                  ))
                }
                {
                  index !== course.prerequisites.length - 1 ? <span>OR</span> : <span></span>
                }
              </p>
            ))}
          </section>
        ) : (
          <div className='hidden'></div>
        )
      }
    </div>
  )
}
