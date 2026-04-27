import Avatar from '../Avatar'
import MeowIcon from '../MeowLogo/MeowLogo'
import './Header.css'

const Header = () => {
  return (
    <header className='header'>
        <MeowIcon />
        <Avatar />
    </header>
  )
}

export default Header