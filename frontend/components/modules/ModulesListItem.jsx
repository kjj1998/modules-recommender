import React from 'react'
import Link from 'next/link'

export default function ModulesListItem({ module }) {
  return (
    <div className='mb-16'>
      <h2 className='mb-3 text-xl font-semibold text-blue-600 hover:text-blue-800 mt-1'>
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
      <section className='mb-4'>
        <p className='font-bold'>
          Broadening And Deepening:
        </p>
        <p className='text-slate-600'>
          {module.broadeningAndDeepening === true ? "Yes" : "No"}
        </p>
      </section>
      {/* <section className='mb-4'>
        <p className='font-bold'>
          Mutually Exclusive with:
        </p>
        <p className='text-slate-600'>AB0601, HW0105, HW0106, HW0111, HW0128, HW0188, HW0209</p>
      </section> */}
      {
        module.prerequisites && module.prerequisites.length > 0 ? (
          <section className='mb-4'>
            <p className='font-bold'>
              Prerequisite(s) :
            </p>
            {module.prerequisites.map((prereqGroup, index) => (
              <p key={index} className='text-slate-600'>
                {
                  prereqGroup.map((prereq) => (
                    <span key={prereq} >{prereq} </span>
                  ))
                }
                {
                  index !== module.prerequisites.length - 1 ? <span>OR</span> : <span></span>
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
