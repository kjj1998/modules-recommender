import React, { useEffect, useState } from 'react'
import Login from '@/components/auth/Login'
import { getSession } from 'next-auth/react'
import { useRouter } from 'next/router'

export default function LoginPage() {
  const router = useRouter()
  const [IsLoading, setIsLoading] = useState(true)

  useEffect(() => {
    getSession().then(session => {
      if (session) {
        router.replace('/')
      } else {
        setIsLoading(false)
      }
    })
  }, [router])

  if (IsLoading) {
    return <p>Loading...</p>
  }

  return (
    <Login />
  )
}
