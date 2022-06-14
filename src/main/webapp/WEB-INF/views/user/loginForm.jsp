<%@ page language="java" contentType="text/html" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ include file="../layout/header.jsp"%>
<div class="container">
    <form action="/auth/loginProc" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
        </div>

<%--        <div class="form-group form-check">
            <label class="form-check-label">
                <input name="remember" class="form-check-input" type="checkbox"> Remember me
            </label>
        </div>--%>
        <button id="btn-login" class="btn btn-primary" style="float: left;">로그인</button>
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=f0a63e75764e9db54fc9a14e157f8e0f&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img style="height: 38px;" src="/images/kakaoButton.png" alt="kakao_login" /></a>
    </form>
    
</div>
<br />

<%@ include file="../layout/footer.jsp"%>


