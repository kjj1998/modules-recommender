import React from 'react'
import Container from '../Container'
import Logo from './Logo'
import Search from './Search'
import UserMenu from './UserMenu'

const Navbar = () => {
  return (
    <div className='fixed w-full bg-white z-10 shadow-sm top-0'>
      <div className='py-4 border-b-[1px]'>
        <Container>
          <div className='flex flex-row items-center justify-between gap-3 md:gap-0'>
            <Logo />
            <div className='relative'>
              <div className='flex flex-row items-center gap-3'>
                <Search />
                <UserMenu />
              </div>
            </div>
          </div>
        </Container>
      </div>
    </div>
  )
}

export default Navbar