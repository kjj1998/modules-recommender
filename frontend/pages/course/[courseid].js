import React from 'react'
import { useRouter } from 'next/router'

function Course() {
  const router = useRouter();

  console.log(router.pathname)
  console.log(router.query)

  return (
    <div>Course</div>
  )
}

export default Course