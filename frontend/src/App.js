import { Routes, Route } from 'react-router-dom';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { fetchUserInfo } from './features/user/userActions';
import './App.css';
// Main
import Layout from './layout/Layout';
import MainPage from './pages/main/MainPage';
import OnlyHeaderLayout from './layout/OnlyHeaderLayout';
// User
import LoginPage from './pages/user/LoginPage';
import ProfilePage from './pages/user/ProfilePage';
import ShareFcPage from './pages/share/SharedFcPage';
import DetailSharedFcPage from './pages/share/DetailSharedFcPage';
// Plan
import PlanPage from './pages/trip/PlanPage';
import DetailFullcoursePage from './pages/user/DetailFullcoursePage';
// survey
import RecommendPage from './pages/survey/RecommendPage';
import SurveyPage from './pages/survey/SurveyPage';
// 404
import NotFound from './pages/NotFound';
import ProtectedLoginRoute from './lib/ProtectedLoginRoute';
import ProtectedRoute from './lib/ProtectedRoute';
import { useSelector } from 'react-redux';

function App() {
  const dispatch = useDispatch();

  const { userInfo } = useSelector((state) => state.user);

  useEffect(() => {
    dispatch(fetchUserInfo());
  }, [dispatch]);

  return (
    <div className="App">
      <Routes>
        {/* Main */}
        <Route path="" element={<MainPage />} />
        {/* user */}
        <Route path="user" element={<Layout />}>
          <Route element={<ProtectedLoginRoute userInfo={userInfo} />}>
            <Route path="login" element={<LoginPage />} />
          </Route>
          <Route element={<ProtectedRoute userInfo={userInfo} />}>
            <Route path="profile/:pageNum" element={<ProfilePage />} />
            <Route path="fullcourse/:fcId" element={<DetailFullcoursePage />} />
          </Route>
        </Route>
        <Route path="fullcourse" element={<Layout />}>
          <Route path="" element={<ShareFcPage />} />
          <Route path="detail/:sharedFcId" element={<DetailSharedFcPage />} />
        </Route>
        {/* trip */}
        <Route path="trip" element={<OnlyHeaderLayout />}>
          <Route element={<ProtectedRoute userInfo={userInfo} />}>
            <Route path="plan" element={<PlanPage />} />
            {/* survey 일정짜기 전 설문조사 */}
            <Route path="survey" element={<SurveyPage />} />
            <Route path="recommend" element={<RecommendPage />} />
          </Route>
        </Route>

        <Route path="*" element={<NotFound />} />
      </Routes>
    </div>
  );
}

export default App;
