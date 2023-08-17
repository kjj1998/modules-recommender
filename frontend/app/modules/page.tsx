import React from 'react'
import Search from './components/Search'
import Item from './components/Item'

export default async function Modules() {
  return (
    <main className="mx-auto max-w-5xl py-1 min-h-screen mt-16">
      <Search />
      <p className='my-2 p-5 text-right border-b border-slate-400 font-semibold'>
        1879 courses found
      </p>
      <Item />
      <Item />
      <Item />
      <Item />
      <Item />
      <nav>
        <ul className='flex mb-4 justify-center items-center'>
          <li className='px-2.5 py-2 mx-1 rounded-lg bg-cyan-400 text-slate-100'>1</li>
          <li className='p-2 mx-1 '>2</li>
          <li className='p-2 mx-1 '>3</li>
          <li className='p-2 mx-1 ' >4</li>
          <li className='p-2 mx-1 ' >5</li>
          <li className='p-2 mx-1 '>
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="block w-5"><polyline points="9 18 15 12 9 6"></polyline></svg>
          </li>
          <li className=''> 
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 20 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="block w-5"><polyline points="13 17 18 12 13 7"></polyline><polyline points="6 17 11 12 6 7"></polyline></svg>
          </li>
        </ul>
      </nav>
    </main>
  )
}
