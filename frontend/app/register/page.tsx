'use client'

import React from 'react'

export default function Register() {

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const payload = {
      username: event.currentTarget.username.value,
      password: event.currentTarget.password.value,
    };

    const data = await fetch("/api/auth/login",  {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    });

    const res = await data.json()

    console.log(res)

    alert(res);
  }

  return (
    <main className='mx-auto max-w-5xl py-1 min-h-screen'>
      <div className="p-24 flex items-center justify-center">
        <div className="max-w-lg">
          <h1 className="text-center px-48 text-2xl font-bold text-slate-100 rounded-t-md bg-sky-600">
            Register
          </h1>
          <div className='p-9 bg-slate-100 rounded-b-md'>
            <form onSubmit={handleSubmit} className="flex flex-col gap-4">
              <div className='self-center p-5'>
                <div className='p-3'>
                  <label htmlFor="username" className='font-semibold pr-2'>Username:</label>
                  <input type="text" id="username" name="username" required className="border rounded border-black indent-2" />
                </div>
                <div className='p-3'>
                  <label htmlFor="password" className='font-semibold pr-3'>Password:</label>
                  <input type="password" id="password" name="password" required className="border rounded border-black indent-2"/>
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
