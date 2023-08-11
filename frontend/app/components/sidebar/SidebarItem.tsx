'use client'

import { Icon } from 'next/dist/lib/metadata/types/metadata-types';
import React from 'react'
import type { IconType } from 'react-icons'

interface SidebarItemProps {
  onClick: () => void;
  label: string;
  Icon: IconType;
}

const SidebarItem: React.FC<SidebarItemProps> = ({
  onClick, label, Icon
}) => {
  return (
    <div className='flex flex-row m-5 border-red-500 border-2 cursor-pointer hover:shadow-md' onClick={() => {alert("Hello World!")}}>
      <div className='pr-3'>
        <Icon size={30} />
      </div>
      <div className='self-center'>
        {label}
      </div>
    </div>
  )
}

export default SidebarItem