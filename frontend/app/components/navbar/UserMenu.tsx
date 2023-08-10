'use client'

import React, { useCallback, useState } from 'react'
import { AiOutlineMenu } from 'react-icons/ai'
import Avatar from '../Avatar'
import MenuItem from './MenuItem'

const UserMenu = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleOpen = useCallback(() => {
    setIsOpen((value) => !value)
  }, [])

  return (
    <div onClick={toggleOpen}
      className=" 
        p-4 md:py-1 md:px-2 border-[1px] md:w-auto
        border-emerald-400 flex flex-row items-center 
        gap-3 rounded-full cursor-pointer hover:shadow-md 
        transition
      "
    >
      <AiOutlineMenu />
      <div>
        <Avatar />
      </div>

      {isOpen && (
        <div className='absolute rounded-xl shadow-md w-[40vw]
          md:w-3/4 bg-white overflow-hidden right-0
          top-12 text-sm
        '>
          <div className='flex flex-col cursor-pointer'>
            <>
              <MenuItem
                onClick={() => {}}
                label="Login"
              />
              <MenuItem
                onClick={() => {}}
                label="Sign Up"
              />
            </>
          </div>
          
        </div>
      )}
    </div>
  )
}

export default UserMenu