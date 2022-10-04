import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import {
  createSharedFcComment,
  createSharedFcLike,
  dropSharedFcComment,
} from '../../../features/share/shareActions';
import styled from 'styled-components';
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';

const CommentBlock = styled.div`
  background: aliceblue;
  height: 100vh;
  width: 100vw;

  position: absolute;
  z-index: 5;
  top: 0;
  height: 100vh;
  /* background:white; */

  width: 100%;

  .postBtn {
    width: 4rem;
  }

  .detail {
    margin: 40px 30px 30px 30px;
    color: #333333;
    font-size: 20px;
  }

  .commentForm {
    margin: 30px 15px 20px 20px;

    form {
      border: 1px solid #078ec4;
      border-radius: 10px;

      background: white;
      display: flex;
      justify-content: space-between;
    }
  }

  input {
    border: #ffffff;
    width: calc(100%- 4rem);
    border-radius: 10px;
    margin-right: 10px;
    padding: 6px 0px 6px 10px;
    font-size: 17px;

    :focus {
      outline: none;
    }
  }

  button {
    cursor: pointer;
    border: #ffffff;
    background-color: #ffffff;
    padding: 3px 10px;
    color: #078ec4;
    border-radius: 20px;

    &:hover {
      color: #0abdff;
      font-weight: bold;
    }
  }

  .commentArea {
    height: 50vh;
    margin: 0.5rem;
    overflow-y: scroll;
    background: white;
    border: 3px solid #bbbbbb;
    border-radius: 0.5rem;
  }

  .commentBox {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin: 20px 10px;
    align-items: center;

    button {
      cursor: pointer;
      border: #ffffff;
      background-color: #ffffff;
      color: #b22323;
      border-radius: 20px;
      &:hover {
        color: #f73131;
        font-weight: bold;
      }
    }

    #profileImg {
      width: 2.3rem;
      height: 2.3rem;
      border-radius: 20px;
    }
  }

  .comment {
    list-style: none;
    font-size: 15px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  #userNickname {
    font-weight: bold;
    margin: 0px 7px;
  }

  .likeArea {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .favorite {
    cursor: pointer;
    color: #f73131;
    margin-right: 5px;
  }

  .favoriteborder {
    cursor: pointer;
    color: #f73131;
    margin-right: 5px;
  }

  #btnKakao {
    position: relative;
    display: inline-block;
    cursor: pointer;
    padding-top: 40px;
    margin-left: 20px;
    background-image: url(https://onsikgo.s3.ap-northeast-2.amazonaws.com/icon/icon-kakao.png);
    background-repeat: no-repeat;
    width: 50px;
  }
`;

const MobileComment = ({ sharedFcInfo }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [comment, setComment] = useState('');
  const { Kakao } = window;
  const { userInfo } = useSelector((state) => state.user);

  const onChangeReply = (e) => {
    setComment(e.target.value);
  };

  const onSubmit = (e) => {
    e.preventDefault();
    if (!userInfo) {
      navigate('/user/login');
    } else {
      dispatch(
        createSharedFcComment({ comment, sharedFcId: sharedFcInfo.sharedFcId }),
      );
      e.target.reset();
    }
  };

  const onClickDelete = (comment) => {
    dispatch(
      dropSharedFcComment({
        sharedFcId: sharedFcInfo.sharedFcId,
        commentId: comment.commentId,
      }),
    );
  };

  const onClickLike = () => {
    dispatch(createSharedFcLike(sharedFcInfo.sharedFcId));
  };

  const shareKakao = (sharedFcInfo) => {
    Kakao.Link.createDefaultButton({
      container: '#btnKakao',
      objectType: 'feed',
      content: {
        title: sharedFcInfo.title,
        description: sharedFcInfo.detail,
        imageUrl: sharedFcInfo.thumbnail,
        link: {
          mobileWebUrl:
            'https://j7e106.p.ssafy.io/fullcourse/detail/' + sharedFcInfo.fcId,
          webUrl:
            'https://j7e106.p.ssafy.io/fullcourse/detail/' + sharedFcInfo.fcId,
        },
      },
    });
  };

  return (
    <CommentBlock>
      {sharedFcInfo ? (
        <div className="detail">{sharedFcInfo.detail}</div>
      ) : null}
      <div className="likeArea">
        {sharedFcInfo ? (
          <>
            {sharedFcInfo.like ? (
              <FavoriteIcon className="favorite" onClick={onClickLike} />
            ) : (
              <FavoriteBorderIcon
                className="favoriteborder"
                onClick={onClickLike}
              />
            )}
            <span>좋아요</span>
          </>
        ) : null}
        {sharedFcInfo ? (
          <a
            id="btnKakao"
            onClick={() => {
              shareKakao(sharedFcInfo);
            }}
          ></a>
        ) : null}
        <span>공유하기</span>
      </div>
      <div className="commentForm">
        <form onSubmit={onSubmit}>
          <input
            type="text"
            placeholder="댓글 달기.."
            onChange={onChangeReply}
          />
          <button className="postBtn">게시</button>
        </form>
      </div>
      <div className="commentArea">
        {sharedFcInfo ? (
          <>
            {sharedFcInfo.sharedFCComments.map((comment, index) => {
              return (
                <div key={index} className="commentBox">
                  <li className="comment">
                    <img
                      id="profileImg"
                      src={comment.imgUrl}
                      alt="profileImg"
                    />
                    <span id="userNickname">{comment.nickname} </span>
                    <span>{comment.comment}</span>
                  </li>
                  {userInfo && userInfo.email === comment.email ? (
                    <button onClick={() => onClickDelete(comment)}>삭제</button>
                  ) : null}
                </div>
              );
            })}
          </>
        ) : null}
      </div>
    </CommentBlock>
  );
};

export default MobileComment;
