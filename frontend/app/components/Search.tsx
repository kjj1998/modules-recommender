'use client';

import React, { useState } from 'react'
import { BiSearch } from 'react-icons/bi';

const Search = () => {
  const [search, setSearch] = useState('')


  return (
    // Overall box containing search elements
    <div 
      className="
        border-[1px] w-full md:w-auto py-1
        rounded-full shadow-sm hover:shadow-md transition
        cursor-pointer border-rose-600 flex flex-row
      "
    >
      <div 
        className="
          text-sm pl-6 pr-2 text-gray-600 
          flex flex-row items-center gap-3
        "
      >
        <div className="text-sm font-semibold px-6">
          Enter search term or course code
        </div>
        <div className="p-2 bg-rose-500 rounded-full text-white">
          <BiSearch size={16}/>
        </div>
      </div>
    </div>
  )
}

export default Search