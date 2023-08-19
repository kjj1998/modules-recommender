import React from 'react'
import { getCourses } from '../../../lib/getCourseData'
import Search from '../../components/Search'
import Item from '../../components/Item'
import Link from 'next/link'

import PaginationNavbar from '../../components/PaginationNavbar'

type Props = {
  params: {
    page: string,
  }
}

export default async function page({ params: { page } }: Props) {
  let pageNum: number = parseInt(page)

  const modules = await getCourses((pageNum - 1) * 10, 10)
  
  const totalNumOfModules = modules?.at(0)?.total ?? 0
  const totalNumOfPages = Math.floor(totalNumOfModules / 10) + (totalNumOfModules % 10 !== 0 ? 1 : 0)

  const pageNumbers: number[] = [];
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
        {modules?.map(module => <Item key={module.courseCode} course={module} />)}
      </ul>
      <PaginationNavbar pageNum={pageNum} pageNumbers={pageNumbers} totalNumOfPages={totalNumOfPages} />
    </main>
  )
}