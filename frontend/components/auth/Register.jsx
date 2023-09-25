import React, { useState, useContext } from 'react'
import NotificationContext from '@/store/notification-context'

export default function Register() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [email, setEmail] = useState('')

  const notificationContext = useContext(NotificationContext)

  async function handleSubmit(event) {
    event.preventDefault();

    const reqBody = {
      studentId: username,
      password: password,
      email: email
    }

    try {
      notificationContext.showNotification({
        message: 'Registering new user ...',
        status: 'pending'
      })

      const response = await fetch('/api/auth/signup', {
        method: 'POST',
        body: JSON.stringify(reqBody),
        headers: {
          'Content-Type': 'application/json',
        }
      })

      const result = await response.json()

      if (result.code === 400 || result.code === 500) {
        throw new Error(result.status, { cause: result.code })
      }

      notificationContext.showNotification({
        message: result.status,
        status: result.code
      })

    } catch (error) {
      notificationContext.showNotification({
        message: error.message || 'Something went wrong!',
        status: error.cause
      })
    } finally {
      setEmail('')
      setPassword('')
      setUsername('')
    }
  }

  return (
    <main className='mx-auto max-w-5xl py-1 min-h-screen'>
      <div className="p-24 flex items-center justify-center">
        <div className="max-w-lg">
          <h1 className="text-center px-48 text-2xl font-bold text-slate-100 rounded-t-md bg-sky-600 py-3">
            Register
          </h1>
          <div className='p-9 bg-slate-100 rounded-b-md'>
            <form onSubmit={handleSubmit} className="flex flex-col gap-4">
              <div className='self-center p-5'>
                <div className='p-3'>
                  <label htmlFor="email" className='font-semibold pr-3 mr-8'>Email:</label>
                  <input value={email} type="email" id="email" onChange={e => setEmail(e.target.value)} name="email" required className="border rounded border-black indent-2 w-72" />
                </div>
                <div className='p-3'>
                  <label htmlFor="username" className='font-semibold pr-2'>Username:</label>
                  <input value={username} type="text" id="username" onChange={e => setUsername(e.target.value)} name="username" required className="border rounded border-black indent-2 w-72" />
                </div>
                <div className='p-3'>
                  <label htmlFor="password" className='font-semibold pr-3'>Password:</label>
                  <input value={password} type="password" id="password" onChange={e => setPassword(e.target.value)} name="password" required className="border rounded border-black indent-2 w-72"/>
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
