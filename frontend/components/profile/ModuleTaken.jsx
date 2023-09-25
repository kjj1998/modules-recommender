import React from 'react'

export default function ModuleTaken({ modulesTaken, setModulesTaken, courseCode, module }) {

  function removeModule(moduleToBeRemoved) {
    setModulesTaken(modulesTaken.filter((module) => module !== moduleToBeRemoved))
  }
  
  return (
    <div key={module} className='mx-1 my-1.5 flex flex-row border w-24 rounded-lg bg-violet-500'>
      <span className='p-1 pl-1.5 font-medium text-white align-middle'>{courseCode}</span>
      <button onClick={() => removeModule(module)} type="button" className='w-min m-1 border rounded-full border-blue-400 focus:ring-gray-200 hover:bg-slate-400 bg-slate-300'>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" className="w-5 h-5">
          <path d="M6.28 5.22a.75.75 0 00-1.06 1.06L8.94 10l-3.72 3.72a.75.75 0 101.06 1.06L10 11.06l3.72 3.72a.75.75 0 101.06-1.06L11.06 10l3.72-3.72a.75.75 0 00-1.06-1.06L10 8.94 6.28 5.22z" />
        </svg>
      </button>
    </div>
  )
}
