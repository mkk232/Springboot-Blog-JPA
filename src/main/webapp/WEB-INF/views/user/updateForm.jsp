<%@ page language="java" contentType="text/html" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ include file="../layout/header.jsp"%>
<div class="container">
    <form>
        <input type="hidden" id="id" value="${principal.user.id}">
        <div class="form-group">
            <label for="username">Username</label>
            <input value="${principal.user.username}" type="text" class="form-control" placeholder="Enter username" id="username" readonly>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" placeholder="Enter password" id="password">
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input value="${principal.user.email}" type="email" class="form-control" placeholder="Enter email" id="email" >
        </div>

    </form>
    <button id="btn-update" class="btn btn-primary">회원수정완료</button>
</div>
<br />
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>

