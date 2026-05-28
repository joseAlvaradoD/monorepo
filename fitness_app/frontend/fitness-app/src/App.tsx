import { Box, Button } from "@mui/material"
import { useContext, useEffect, useState } from "react"
import { AuthContext } from "react-oauth2-code-pkce"
import { useDispatch } from "react-redux"
import { BrowserRouter as Router, Navigate, Route, Routes, useLocation } from "react-router"
import { setCredentials } from "./store/authSlice"
import ActivityList from "./components/activityList"
import ActivityForm from "./components/activityForm"
import ActivityDetail from "./components/activityDetail"

const ActivitiesPage = () => {
  return (
    <Box>
      <ActivityForm onActivitiesAdded = {() => window.location.reload()}/>
      <ActivityList />
    </Box>
  )
};

function App() {
  const { token, tokenData, logIn, logOut, isAuthenticated } = useContext(AuthContext);
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);

  useEffect(() => {
    if(token){
      dispatch(setCredentials({token, user: tokenData}));
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch]);

  return (
    <Router>
      {!token ? (
      <Button onClick={()=>{
        logIn();
      }} variant="contained">Login</Button>
    ) : (
      <Box component="section" sx={{ p: 2}}>
        <Routes>
          <Route path="/activities" element={<ActivitiesPage/>}/>
          <Route path="/activities/:id" element={<ActivityDetail/>}/>
          <Route path="/" element={token ? <Navigate to="/activities" replace/> : <div>Welcome please Login</div>}/>
        </Routes>
      </Box>
    )}
    </Router>
  )
}

export default App
