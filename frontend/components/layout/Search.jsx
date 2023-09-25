import React, { useState } from 'react'
import { useRouter } from 'next/router'

export default function Search() {
  const [searchTerm, setSearchTerm] = useState('')
  const router = useRouter()

  function onSearch(e) {
    e.preventDefault()

    if (searchTerm === '') {
      router.push(`/courses/page/1`)
    } else {
      const encodedSearchQuery = encodeURI(searchTerm)
      router.push(`/search/${encodedSearchQuery}/1`)
    }
  }
  
  return (
    <form className='w-full border-b py-2 border-slate-400 flex flex-row sticky top-20 bg-white' onSubmit={onSearch}>
      <button className="p-1 ml-1 font-bold align-middle">
        {/* <svg className="text-slate-400 h-5 w-5 hover:bg-slate-600" viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd" />
        </svg> */}
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-6 h-6">
          <path strokeLinecap="round" strokeLinejoin="round" d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z" />
        </svg>
      </button>
      <input 
        type="text"
        value={searchTerm}
        onChange={(event) => setSearchTerm(event.target.value)}
        className= "p-2 w-full text-xl border-0 focus:border-transparent bg-transparent focus:outline-none appearance-none border-transparent outline-none focus:ring-0"
        placeholder="Enter course codes, names, or descriptions"
      />
    </form>
  )
}
