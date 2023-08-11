'use client'

import React from 'react'
import SidebarItem from './SidebarItem'
import { BiBookOpen, BiLogIn } from 'react-icons/bi'
import { AiFillHome } from 'react-icons/ai'
import { HiThumbUp } from 'react-icons/hi'

const Sidebar = () => {
  return (
    <div className='fixed inset-y-0 left-0 pt-20 border-blue-400 border-2'>
      <ul>
        <li>
          <SidebarItem 
            onClick={() => {}}
            label="Home"
            Icon = {AiFillHome}
          />
        </li>
        <li>
          <SidebarItem 
            onClick={() => {}}
            label="Modules"
            Icon = {BiBookOpen}
          />
        </li>
        <li>
          <SidebarItem 
            onClick={() => {}}
            label="Recommendations"
            Icon = {HiThumbUp}
          />
        </li>
        <li>
          <SidebarItem 
            onClick={() => {}}
            label="Login"
            Icon = {BiLogIn}
          />
        </li>
      </ul>
    </div>
  )
}

export default Sidebar