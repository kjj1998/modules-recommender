import React from 'react'
import Link from 'next/link'

export default function ModulesListItem({ module }) {
  const words = module.courseName.split(" ")
  const capitalizedCourseName = words.map((word) => {
    return word[0].toUpperCase() + word.substring(1).toLowerCase()
  }).join(" ")

  return (
    <div className='mb-16'>
      <h2 className='mb-3 text-xl font-semibold text-blue-600 hover:text-blue-800 mt-1'>
        <Link href={`/courses/${module.courseCode}`}>
          {module.courseCode} <span>{capitalizedCourseName}</span>
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
      {
        module.mutuallyExclusives && module.mutuallyExclusives.length > 0 ? (
          <section className='mb-4'>
            <p className='font-bold'>
              Mutually Exclusives(s) :
            </p>
            {module.mutuallyExclusives.map((mutual) => (
              <p key={mutual} className='text-slate-600'>
                {mutual}
              </p>
            ))}
          </section>
        ) : (
          <div className='hidden'></div>
        )
      }
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
