import React from 'react'
import Link from 'next/link'

interface Props {
  pageNum: number;
  pageNumbers: number[];
  totalNumOfPages: number;
}

const PaginationNavbar: React.FC<Props> = ({ pageNum, pageNumbers, totalNumOfPages }) => {
  return (
    <nav>
        <ul className='flex mb-4 justify-center items-center'>
          {
            pageNum !== 1 ? (
              <li className='p-1 mx-1'>
                <Link href={`/courses/${1}`}>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 20 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="block w-5"><polyline points="11 17 6 12 11 7"></polyline><polyline points="18 17 13 12 18 7"></polyline></svg>
                </Link>
              </li>
            ) : <div className='hidden'></div>
          }
          {
            pageNum !== 1 ? (
              <li className='p-1 mx-1'>
                <Link href={`/courses/${pageNum - 1}`}>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="block w-5"><polyline points="15 18 9 12 15 6"></polyline></svg>
                </Link>
              </li>
            ) : <div className='hidden'></div>
          }
          {
            pageNumbers.map((pageNumber) => {
              return (
                pageNumber == pageNum ?
                <li key={pageNumber} className=' font-semibold px-2.5 py-1.5 mx-1 rounded-lg bg-cyan-400 text-slate-100'>
                  {pageNumber}
                </li>
                :
                <li key={pageNumber} className='font-semibold p-1.5 mx-1'>
                  <Link href={`/courses/${pageNumber}`}>{pageNumber}</Link>
                </li>
              )
            })
          }
          {
            pageNum !== totalNumOfPages ? (
              <li className='p-1 mx-1'>
                <Link href={`/courses/${pageNum + 1}`}>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="block w-5"><polyline points="9 18 15 12 9 6"></polyline></svg>
                </Link>
              </li>
            ) : <div className='hidden'></div>
          }
          {
            totalNumOfPages > 9 && pageNum !== totalNumOfPages ? (
              <li className='p-1 mx-1'>
                <Link href={`/courses/${totalNumOfPages}`}>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 20 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="block w-5"><polyline points="13 17 18 12 13 7"></polyline><polyline points="6 17 11 12 6 7"></polyline></svg>
                </Link>
              </li>
            ) : <div className='hidden'></div>
          }
        </ul>
      </nav>
  )
}

export default PaginationNavbar