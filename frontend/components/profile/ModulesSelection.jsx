import React, { useEffect, Fragment, useState } from 'react'
import ModuleTaken from './ModuleTaken'

export default function ModulesSelection({ faculties, modulesForFaculty, modulesTaken, setModulesTaken, setModulesForFaculty, faculty, setFaculty }) {
  const [facultyModulesDict, setFacultyModulesDict] = useState({})

  useEffect(() => {
    const fetchCourses = async () => { 
      const response = await fetch(`/api/modules/faculty/${faculty}`)
      const data =  await response.json()

      const courses = data.data.map((course) => course["courseCode"] + " " + course["courseName"])

      const modules = [""].concat(courses)

      setModulesForFaculty(modules)
    }

    fetchCourses()
  }, [faculty, setModulesForFaculty])

  function addModulesTaken(module) {
    if (module !== "" && !modulesTaken.includes(module)) {
      setModulesTaken(modulesTaken => [...modulesTaken, module])
    }
  }
  
  return (
    <div className='mb-6'>
      <h2 className='block mb-2 text-lg font-medium text-gray-900 dark:text-white'>Modules</h2>
      <div className='flex flex-row justify-between mb-5'>
        <div className='w-5/12 mr-2'>
          <label htmlFor="faculty" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Faculty</label>
          <select id="faculty" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" onChange={(e) => setFaculty(e.target.value)}>
            { faculties.map((faculty) => <option key={faculty}>{faculty}</option>) }
          </select>
        </div>
        <div className='w-7/12'>
          <label htmlFor="faculty" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Module</label>
          <select id="faculty" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" onChange={(e) => addModulesTaken(e.target.value.substring(0, 6))}>
            {
              modulesForFaculty.map((module) => {
                return <option key={module}>{module}</option>
              }
            )}
          </select>
        </div>
      </div>

      <h2 className='block mb-2 text-sm font-medium text-gray-900 dark:text-white'>Modules taken</h2>
      {
        modulesTaken.length !== 0 ? 
          <div className='bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg flex flex-row flex-wrap'>
            { 
              modulesTaken.map((module) => 
                <ModuleTaken 
                  key={module} 
                  module={module} 
                  modulesTaken={modulesTaken} 
                  setModulesTaken={setModulesTaken} 
                  courseCode={module} 
                />
            )}
          </div>
        : 
          <Fragment></Fragment>
      }
    </div>
  )
}
