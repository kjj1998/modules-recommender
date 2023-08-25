'use client'

import React, { useEffect, useState } from 'react'
import { cookies } from "next/headers";
import { useRouter } from "next/navigation";

export default function Profile() {
  const [isSuccess, setIsSuccess] = useState(false);
  const { push } = useRouter();

  useEffect(() => {
    (async () => {
      const status = await getUser();

      if (status === 200) {
        setIsSuccess(true);
      }

      // if the error did not happen, if everything is alright
      
      console.log(status)
    })();
  }, []);

  return (
    isSuccess ? 
    (
      <div className='mx-auto max-w-5xl py-1 min-h-screen mt-16'>
        <h2 className='text-xl my-5 font-semibold'>
          Hi John, based on the modules you have taken the following modules are recommended:
        </h2>
      </div>
    ) : 
    <div className='mx-auto max-w-5xl py-1 min-h-screen mt-16'>
        <h2 className='text-xl my-5 font-semibold'>
          Not authenticated
        </h2>
      </div>
  )
}

async function getUser() {
  
  const data = await fetch("/api/auth/check")

  return data.status
}