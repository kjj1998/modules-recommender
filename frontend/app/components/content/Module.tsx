'use client'

import React from 'react'
import { BsDot } from 'react-icons/bs'

const Module = () => {
  return (
    <div className='bg-slate-300 p-5'>
      <div className='flex flex-row'>
        <div className='pr-3 text-xl font-bold text-sky-600'>
          CZ2005
        </div>
        <div className='text-xl font-bold text-sky-600'>
          OPERATING SYSTEMS
        </div>
      </div>
      <div className='flex flex-row py-3'>
        <div>
          SCSE
        </div>
        <div className='self-center'>
          <BsDot />
        </div>
        <div>
          3 AU
        </div>
      </div>
      <div className='pb-3'>
        This course teaches you rubbish.
      </div>
      <div className='font-bold'>
        Prerequisites (requires one of the following module(s)):
      </div>
      <div>
        <ul>
          <li>CZ1005</li>
          <li>SC1005</li>
        </ul>
      </div>
    </div>
  )
}

export default Module