import React from 'react'
import Search from '@/components/layout/Search'
import ModulesListItem from '@/components/modules/ModulesListItem'
import { useRouter } from 'next/router'
import PaginationNavBar from '@/components/layout/PaginationNavBar'
import { paginate } from '@/lib/utils'
import { fetchModules } from '@/lib/fetchModuleData'

function CoursesPage(props) {
  const { loadedModules } = props
  const router = useRouter()
  const totalNumOfModules = loadedModules && loadedModules.length > 0 ? loadedModules[0].total : 0
  const maxNumOfPages = Math.ceil(totalNumOfModules / 10)

  let {current, prev, next, items} = paginate(parseInt(router.query.pagenumber), maxNumOfPages)

  return (
    <main className="mx-auto max-w-5xl py-1 min-h-screen mt-16">
      <Search />
      <p className='my-2 p-5 text-right border-b border-slate-400 font-semibold'>
        {totalNumOfModules} modules found
      </p>
      <ul>
        { loadedModules.map((module) => <ModulesListItem key={module.courseCode} module={module} /> ) }
      </ul>
      <PaginationNavBar current={current} prev={prev} next={next} items={items} mode='view'/>
    </main>
  )
}

// Removed static site generation because the backend api is not online at build time

// export async function getStaticProps(context) {
//   const { params } = context
//   const pagenumber = params.pagenumber
//   const skip = (pagenumber - 1) * 10

//   const response = await fetch(`http://localhost:8081/api/v1/modules/${skip}/10`)
//   if (!response.ok) return undefined
//   const data = await response.json()
//   const modules = data.data

//   if (!modules) {
//     return { notFound: true}
//   }

//   return {
//     props: {
//       loadedModules: modules,
//     },
//     revalidate: 60 * 60,
//   }
// }

// export async function getStaticPaths() {
//   const response = await fetch('http://localhost:8081/api/v1/modules/numberOfModules')
//   const data = await response.json()
//   const numOfModules = data.data

//   const numOfPages = Math.ceil(numOfModules / 10)
//   let pages = Array.from({length: numOfPages}, (_, i) => i + 1)
//   const pathsWithParams = pages.map((page) => ({ params: { pagenumber : page.toString() } }))

//   return {
//     paths: pathsWithParams,
//     fallback: false
//   }
// }

export async function getServerSideProps(context) {
  const { params } = context
  const pagenumber = params.pagenumber
  const skip = (pagenumber - 1) * 10

  const modules = await fetchModules(skip)

  if (!modules) {
    return { notFound: true}
  }

  return {
    props: {
      loadedModules: modules,
    }
  }
}



export default CoursesPage