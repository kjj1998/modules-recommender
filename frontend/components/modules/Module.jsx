import React from 'react'

export default function Module({ course }) {
  return (
    <main className='mx-auto max-w-5xl py-1 min-h-screen mt-10'>
      <div className='mx-auto max-w-5xl py-1 min-h-screen mt-16'>
        <header>
          <h1 className='text-3xl font-bold text-slate-400'>
            <span className='text-blue-600 text-4xl font-extrabold'>{course.courseCode}</span> <br></br>
            {course.courseName}
          </h1>
        </header>
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
        { course.prerequisites && course.prerequisites.length > 0 ? (
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
    </main>
  )
}
