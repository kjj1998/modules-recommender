import React from 'react'
import { fetchModulesBySearchTerm } from '@/lib/fetchModuleData';
import Search from '@/components/layout/Search';
import PaginationNavBar from '@/components/layout/PaginationNavBar';
import { paginate } from '@/lib/utils';
import { useRouter } from 'next/router';
import ModulesListItem from '@/components/modules/ModulesListItem';

export default function SearchPage(props) {
  const { modules } = props
  const router = useRouter()
  const totalNumOfModules = modules && modules.length > 0 ? modules[0].total : 0
  const maxNumOfPages = Math.ceil(totalNumOfModules / 10)
  const term = router.query.term[0]
  const page = router.query.term[1]

  let {current, prev, next, items} = paginate(parseInt(page), maxNumOfPages)

  return (
    <main className="mx-auto max-w-5xl py-1 min-h-screen mt-16">
      <Search />
      <p className='my-2 p-5 text-right border-b border-slate-400 font-semibold'>
        {totalNumOfModules} modules found
      </p>
      <ul>
        { modules.map((module) => <ModulesListItem key={module.courseCode} module={module} /> ) }
      </ul>
      <PaginationNavBar current={current} prev={prev} next={next} items={items} mode='search' term={term}/>
    </main>
  )
}

export async function getServerSideProps(context) {
  const term = context.query.term[0];
  const page = context.query.term[1];
  const skip = (parseInt(page) - 1) * 10

  const modules = await fetchModulesBySearchTerm(term, skip)
  
  return {
    props: {
      modules: modules
    }
  }
}