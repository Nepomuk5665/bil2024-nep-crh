import { Routes, Route, Navigate, useNavigate } from 'react-router-dom'
import { useEffect } from 'react'
import './App.css'
import Username from './Username'
import Home from './Home'

function App() {
  const navigate = useNavigate()
  const loggedIn = localStorage.getItem('username') !== null

  useEffect(() => {
    const handleStorageChange = () => {
      const isLoggedIn = localStorage.getItem('username') !== null
      if (!isLoggedIn) {
        navigate('/login')
      } else {
        navigate('/home')
      }
    }

    window.addEventListener('storage', handleStorageChange)
    
    if (!loggedIn) {
      navigate('/login')
    } else {
      navigate('/home')
    }

    return () => {
      window.removeEventListener('storage', handleStorageChange)
    }
  }, [navigate, loggedIn])

  return (
    <Routes>
      <Route path="/login" element={<Username />} />
      <Route path="/home" element={loggedIn ? <Home /> : <Navigate to="/login" />} />
      <Route path="/" element={<Navigate to={loggedIn ? "/home" : "/login"} />} />
    </Routes>
  )
}

export default App