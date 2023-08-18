import React from 'react'
import { getModules } from '../../../lib/getModulesData'
import Search from '../components/Search'
import Item from '../components/Item'
import Link from 'next/link'

type Props = {
  params: {
    page: string,
  }
}

export default async function page({ params: { page } }: Props) {
  let pageNum = parseInt(page)

  const modules = await getModules((pageNum - 1) * 10, 10)
  
  const totalNumOfModules = modules?.at(0)?.total ?? 0
  const totalNumOfPages = Math.floor(totalNumOfModules / 10) + (totalNumOfModules % 10 !== 0 ? 1 : 0)

  const pageNumbers = [];
  pageNumbers.push(pageNum)

  if (pageNum > 5) {
    if (totalNumOfPages - pageNum < 4) {
      for (let i = 1; i <= 9 - (totalNumOfPages - pageNum + 1); i++) {
        pageNumbers.push(pageNum - i)
      }
    } else {
      for (let i = 1; i <= 4; i++) {
        pageNumbers.push(pageNum - i)
      }
    }
  } else {
    for (let i = 1; i < pageNum; i++) {
      pageNumbers.push(i);
    }
  }
  
  if (pageNum <= totalNumOfPages - 4) {
    const curPagesForDisplay = pageNumbers.length

    for (let i = 1; i <= 9 - curPagesForDisplay; i++) {
      pageNumbers.push(pageNum + i);
    }
  } else {
    for (let i = pageNum + 1; i <= totalNumOfPages; i++) {
      pageNumbers.push(i);
    }
  }

  pageNumbers.sort((a, b) => a - b);

  return (
    <main className="mx-auto max-w-5xl py-1 min-h-screen mt-16">
      <Search />
      <p className='my-2 p-5 text-right border-b border-slate-400 font-semibold'>
        {totalNumOfModules} courses found
      </p>
      <ul>
        {modules?.map(module => <Item key={module.courseCode} module={module} />)}
      </ul>
      <nav>
        <ul className='flex mb-4 justify-center items-center'>
          {
            pageNum !== 1 ? (
              <li className='p-1 mx-1'>
                <Link href={`/modules/${1}`}>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 20 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="block w-5"><polyline points="11 17 6 12 11 7"></polyline><polyline points="18 17 13 12 18 7"></polyline></svg>
                </Link>
              </li>
            ) : <div className='hidden'></div>
          }
          {
            pageNum !== 1 ? (
              <li className='p-1 mx-1'>
                <Link href={`/modules/${pageNum - 1}`}>
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
                  <Link href={`/modules/${pageNumber}`}>{pageNumber}</Link>
                </li>
              )
            })
          }
          {
            pageNum !== totalNumOfPages ? (
              <li className='p-1 mx-1'>
                <Link href={`/modules/${pageNum + 1}`}>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="block w-5"><polyline points="9 18 15 12 9 6"></polyline></svg>
                </Link>
              </li>
            ) : <div className='hidden'></div>
          }
          {
            totalNumOfPages > 9 && pageNum !== totalNumOfPages ? (
              <li className='p-1 mx-1'>
                <Link href={`/modules/${totalNumOfPages}`}>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 20 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="block w-5"><polyline points="13 17 18 12 13 7"></polyline><polyline points="6 17 11 12 6 7"></polyline></svg>
                </Link>
              </li>
            ) : <div className='hidden'></div>
          }
        </ul>
      </nav>
    </main>
  )
}