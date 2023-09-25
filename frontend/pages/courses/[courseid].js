import React, { Fragment } from 'react'
import Module from '@/components/modules/Module'
import Head from 'next/head'

function Course(props) {
  let pageHeaderData = (
    <Head>
      <title>Course Information</title>
    </Head>
  )

  const { loadedCourse } = props

  if (!loadedCourse) {
    return <h1 className='mt-20 font-bold text-lg'>Loading ...</h1>
  }

  pageHeaderData = (
    <Head>
      <title>{loadedCourse.courseCode} {loadedCourse.courseName}</title>
    </Head>
  )

  return (
    <Fragment>
      {pageHeaderData}
      <Module course={loadedCourse} />
    </Fragment>
  )
}

export async function getStaticProps(context) {
  const { params } = context
  const courseId = params.courseid

  const response = await fetch(`http://localhost:8081/api/v1/modules/${courseId}`)

  const data = await response.json()
  const course = data.data

  if (!course) {
    return { notFound: true}
  }

  return {
    props: {
      loadedCourse: course,
    },
    revalidate: 60 * 60,
  }
}

export async function getStaticPaths() {
  const response = await fetch('http://localhost:8081/api/v1/modules/courseCodes')
  const data = await response.json()
  const courseCodes = data.data

  const pathsWithParams = 
    courseCodes.map((code) => ({ params: { courseid: code } }))

  return {
    paths: pathsWithParams,
    fallback: false
  }
}

export default Course