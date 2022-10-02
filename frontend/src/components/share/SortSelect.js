import React, { useState, useRef } from 'react';

import styled from 'styled-components';

const Wrapper = styled.div`
  padding: 10px;
  display: flex;
  justify-content: center;
  .selectBox2 * {
    box-sizing: border-box;
  }
  .selectBox2 {
    position: relative;
    width: 150px;
    height: 35px;
    border-radius: 3rem;
    border: 2px solid #0aa1dd;
    cursor: pointer;
    z-index: 99;
  }
  .selectBox2:before {
    content: '▾';
    position: absolute;
    top: 50%;
    font-size: 20px;
    color: #0aa1dd;
    transform: translateY(-50%);
    right: 10px;
    z-index: -1;
  }
  .selectBox2:after {
    content: '';
    display: block;
    width: 2px;
    height: 100%;
    position: absolute;
    top: 0;
    right: 35px;
    background-color: #0aa1dd;
  }

  .selectBox2 .label {
    display: flex;
    align-items: center;
    width: inherit;
    height: inherit;
    border: 0 none;
    outline: 0 none;
    padding-left: 15px;
    background: transparent;
    cursor: pointer;
  }

  .selectBox2 .optionList {
    position: absolute;
    top: 28px;
    left: 0;
    width: 100%;
    background: #d9efff;
    color: #0aa1dd;
    font-size: 15px;
    list-style-type: none;
    padding: 0;
    border-radius: 1rem;
    overflow: hidden;
    max-height: 0;
    transition: 0.3s ease-in;
  }

  .selectBox2 .optionList::-webkit-scrollbar {
    width: 6px;
  }
  .selectBox2 .optionList::-webkit-scrollbar-track {
    background: transparent;
  }
  .selectBox2 .optionList::-webkit-scrollbar-thumb {
    color: #0aa1dd;
    border-radius: 45px;
  }
  .selectBox2 .optionList::-webkit-scrollbar-thumb:hover {
    background: #d9efff;
  }

  .selectBox2.active .optionList {
    max-height: 500px;
  }

  .selectBox2 .optionItem {
    border-bottom: 1px dashed #0aa1dd;
    padding: 5px 15px 5px;
    transition: 0.1s;
  }

  .selectBox2 .optionItem:hover {
    background: #d9efff;
  }

  .selectBox2 .optionItem:last-child {
    border-bottom: 0 none;
  }
`;

const SortSelect = () => {
  const sortItem = [
    {
      type: 'regDate',
      name: '등록순',
    },
    {
      type: 'viewCnt',
      name: '조회순',
    },
    {
      type: 'likeCnt',
      name: '좋아요순',
    },
    {
      type: 'commentCnt',
      name: '댓글순',
    },
  ];
  const [isActive, setIsActive] = useState(false);
  const [sort, setSort] = useState('등록순');

  const showOptions = () => {
    setIsActive(!isActive);
  };

  const onClcikSort = (e) => {
    setSort(e.target.getAttribute('value'));
    setIsActive(!isActive);
  };

  return (
    <Wrapper>
      <div className={isActive ? 'selectBox2 active' : 'selectBox2'}>
        <button className="label" onClick={showOptions}>
          {sort}
        </button>
        <ul className="optionList">
          {sortItem.map((item, index) => {
            return (
              <li
                key={index}
                value={item.name}
                className="optionItem"
                onClick={onClcikSort}
              >
                {item.name}
              </li>
            );
          })}
        </ul>
      </div>
    </Wrapper>
  );
};

export default SortSelect;