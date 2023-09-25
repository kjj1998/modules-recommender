import React, { useState } from 'react'
import { signIn } from "next-auth/react"
import { useRouter } from 'next/router'

export default function Login() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const router = useRouter()

  const handleSubmit = async (event) => {
    event.preventDefault();

    const result = await signIn('credentials', { 
      redirect: false,
      username: username,
      password: password
    })

    console.log(result)

    if (!result.error) {
      router.replace('/profile')
    }
  }

  return (
    <main className='mx-auto max-w-5xl py-1 min-h-screen'>
      <div className="p-24 flex items-center justify-center">
        <div className="max-w-lg">
          <h1 className="text-center px-48 text-2xl font-bold text-slate-100 rounded-t-md bg-sky-600 py-2">
            Login
          </h1>
          <div className='p-9 bg-slate-100 rounded-b-md'>
            <form onSubmit={handleSubmit} className="flex flex-col gap-4">
              <div className='self-center p-5'>
                <div className='p-3'>
                  <label htmlFor="username" className='font-semibold pr-2'>Username:</label>
                  <input value={username} onChange={e => setUsername(e.target.value)} type="text" id="username" name="username" required className="border rounded border-black indent-2" />
                </div>
                <div className='p-3'>
                  <label htmlFor="password" className='font-semibold pr-3'>Password:</label>
                  <input value={password} onChange={e => setPassword(e.target.value)} type="password" id="password" name="password" required className="border rounded border-black indent-2"/>
                </div>
              </div>
              <button type="submit" className="p-2 bg-orange-600 text-white w-fit rounded self-center font-semibold">
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    </main>
  )
}
