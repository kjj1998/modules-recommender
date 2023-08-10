'use client';

import React from 'react'

import Image from 'next/image';
import { useRouter } from 'next/navigation'

const Logo = () => {
  const router = useRouter()

  return (
    <Image
      alt="Logo"
      className="hidden md:block cursor-pointer border-2 border-blue-500"
      height="200"
      width="200"
      src="/images/logo.png"
    />
  )
}

export default Logo