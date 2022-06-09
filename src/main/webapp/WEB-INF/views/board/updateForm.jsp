<%@ page language="java" contentType="text/html" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ include file="../layout/header.jsp"%>
<div class="container">
    <form>
        <input type="hidden" id="id" value="${board.id}">
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Enter title" id="title" value="${board.title}">
        </div>

        <div class="form-group">
            <textarea class="form-control summernote" rows="5" id="content">${board.content}</textarea>
        </div>
    </form>
    <button id="btn-update" class="btn btn-primary">글수정 완료</button>
</div>
<script>
    $('.summernote').summernote({
        placeholder: 'Hello Bootstrap 4',
        tabsize: 2,
        height: 300
    });
</script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>


