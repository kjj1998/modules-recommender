import React from 'react'
import Link from 'next/link'

export default function PaginationNavBar({ current, prev, next, items, mode, term = "" }) {
  
  return (
    <nav>
      <ul className='flex mb-4 justify-center items-center'>
        <li className='p-1 mx-1'>
          {
            prev !== null ?
            (
              <Link href={mode === 'view' ? `/courses/page/${prev}` : `/search/${term}/${prev}`}>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="w-6 h-6 p-0.5 hover:bg-slate-200 bg-slate-100">
                  <path fillRule="evenodd" d="M7.72 12.53a.75.75 0 010-1.06l7.5-7.5a.75.75 0 111.06 1.06L9.31 12l6.97 6.97a.75.75 0 11-1.06 1.06l-7.5-7.5z" clipRule="evenodd" />
                </svg>
              </Link>
            ) : <div className='hidden'></div>
          }
        </li>
        {
          items.map((item, index) => {
            if (item === '…') {
              return <li key={index} className='font-semibold p-1.5 mx-1'>{item}</li>
            } else if (item === current) {
              return <li key={index} className='font-semibold px-2 py-1 mx-1 rounded-lg bg-blue-600 text-slate-100'>{item}</li>
            } else {
              return (<li key={index} className='font-semibold p-1.5 mx-1'>
                <Link href={mode === 'view' ? `/courses/page/${item}` : `/search/${term}/${item}`}>{item}</Link>
              </li>)
            }
          })
        }
        <li className='p-1 mx-1'>
          {
            next !== null ?
            (
              <Link href={mode === 'view' ? `/courses/page/${next}` : `/search/${term}/${next}`}>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="w-6 h-6 p-0.5 hover:bg-slate-200 bg-slate-100">
                  <path fillRule="evenodd" d="M16.28 11.47a.75.75 0 010 1.06l-7.5 7.5a.75.75 0 01-1.06-1.06L14.69 12 7.72 5.03a.75.75 0 011.06-1.06l7.5 7.5z" clipRule="evenodd" />
                </svg>
              </Link>
            ) : <div className='hidden'></div>
          }
        </li>
      </ul>
    </nav>
  )
  
}
