import React from "react";
import { Outlet, Link } from "react-router-dom";
import styled from "styled-components";

const Wrapper = styled.div`
  width: 93vw;
  height: 95vh;
  margin-right: 3vw;

  border: 0.3rem dashed #0AA1DD;
  border-radius: 1rem;

  display: flex;
  align-items: center;
  justify-content: center;

  background: #D9D9D9;

  .navStan{
    position: relative;
    width: calc(100% - 20px);
    height: calc(100% - 20px);
    
    z-index:1;
  }
  
  .innerBox{
    width: 100%;
    height: 100%;
    position: relative;
    border-radius: 1rem;
    border: 0.3rem solid #0AA1DD;
    background: white;

  overflow-y: auto;
  overflow-x: hidden;

  &::-webkit-scrollbar {
    width: 15px;
    border-radius: 1rem;
  }

  &::-webkit-scrollbar-thumb {
    background-clip: padding-box;

    background-color: #0aa1dd;
    /* 스크롤바 둥글게 설정    */
    border-radius: 1rem;
    border: 2px solid transparent;
    width: 5px;
  }

  /* 스크롤바 뒷 배경 설정*/

  &::-webkit-scrollbar-track {
    border-radius: 10px;
    background-color: transparent;
  }
  }
`;

const NavBar = styled.div`
width: 5rem;
height: 20rem;

text-align: right;

position: absolute;
left: calc(100% - 3rem);
top: 5%;


display: flex;
flex-direction: column;
justify-content: space-evenly;

.navItem{
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 4rem;
  font-size: 1rem;
  position: relative;
  background: white;
  padding: 0.5rem;
  border-radius: 0 1rem 1rem 0;

  font-weight: bold;

  font-size: 0.8rem;

  cursor: pointer;

  &:hover{
    left: 3rem;
  }

  span{
    margin-right: 0.5rem;
  }
  
  }
`;

const Layout = () => {
  return (
    <Wrapper>
      <div className='navStan'>
        <NavBar>
          <Link to="/">
        <div className='navItem'><span>메인<br/>페이지</span>🚩</div>
          </Link>
          <Link to="/trip/plan">
        <div className='navItem'><span>풀코스<br/>짜기</span>📝</div>
          </Link>
          <Link to="/fullcourse">
        <div className='navItem'><span>풀코스<br/>탐색</span>🔎</div>
          </Link>
          <Link to="/user/profile/1">
        <div className='navItem'><span>마이<br/>페이지</span>😎</div>
          </Link>
      </NavBar>
      <div className='innerBox'>
        <Outlet />
      </div>
      </div>
    </Wrapper>
  );
};

export default Layout;