import React from 'react'
import Image from 'next/image'


export default function Navbar() {
  return (
    <div className='bg-white sticky border-b-slate-300 border-b w-full flex-none'>
      <div className='max-w-7xl mx-auto'>
        <div className='mx-4 py-4'>
          <div className='relative flex items-center'>
            <Image
              src='/images/logo.png'
              width={200}
              height={200}
              alt = "Logo of student2modules"
              className=''
            />
            <div className='relative flex ml-auto items-center'>
              <nav className='leading-6 font-bold text-slate-700'>
                <ul className='flex space-x-8'>
                  <li>Modules</li>
                  <li>Recommendations</li>
                  <li>Profile</li>
                  <li>Settings</li>
                </ul>
              </nav>
              <div className='flex items-center border-l border-slate-400 ml-6 pl-6'>
                  <Image
                    src='/images/placeholder.jpg'
                    width={20}
                    height={20}
                    alt = "Logo of student2modules"
                    className='border-b-slate-300 border-b rounded-full'
                  />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
