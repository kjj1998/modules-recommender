import React from 'react'
import Image from 'next/image'
import Link from 'next/link'


export default function Navbar() {
  return (
    <div className='bg-white fixed top-0 left-0 right-0 border-b-slate-300 border-b w-full flex-none'>
      <div className='max-w-7xl mx-auto'>
        <div className='mx-4 py-4'>
          <div className='relative flex items-center'>
            <Link href='/'>
              <Image
                src='/images/logo.png'
                width={200}
                height={200}
                alt = "Logo of student2modules"
              />
            </Link>
            <div className='relative flex ml-auto items-center'>
              <nav className='leading-6 font-bold text-slate-700'>
                <ul className='flex space-x-8'>
                  <li>
                    <Link href="/courses/1">Modules</Link>
                  </li>
                  <li>
                    <Link href='#'>Recommendations</Link>
                  </li>
                  <li>
                    <Link href='#'>Profile</Link>
                  </li>
                  <li>
                    <Link href='#'>Settings</Link>
                  </li>
                  <li>
                    <Link href='#'>About</Link>
                  </li>
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
