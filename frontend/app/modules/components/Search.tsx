'use client';

import React, { useState } from 'react'
import { BiSearch } from 'react-icons/bi';

const Search = () => {
  const [search, setSearch] = useState('')


  return (
    <form className='w-full border-b py-2 border-slate-400 flex flex-row sticky top-20 bg-white'>
      <button className="p-1 ml-1 font-bold align-middle">
        <svg className="text-slate-400 h-5 w-5" viewBox="0 0 18 19" fill="currentColor">
          <path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd" />
        </svg>
      </button>
      <input 
        type="text"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        className= "p-2 w-full text-xl"
        placeholder="Enter module codes, names, or descriptions"
      />
    </form>
  )
}

export default Search