let index = {
    init: function () {
        $("#btn-save").on("click", () => {
            this.save();
        });

        $("#btn-delete").on("click", () => {
            this.deleteById();
        });

        $("#btn-update").on("click", () => {
            this.update();
        });
        
        $("#btn-reply-save").on("click", () => {
            this.replySave();
        });
    },

    save: function () {
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        }

        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert("글쓰기가 완료되었습니다.");
            location.href="/";
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },

    deleteById: function () {
        let id = $("#id").text();
        $.ajax({
            type: "DELETE",
            url: "/api/board/" + id,
            dataType: "json"
        }).done(function(resp){
            alert("삭제가 완료되었습니다.");
            location.href="/";
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },

    update: function () {
        let id = $("#id").val();

        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        }

        $.ajax({
            type: "PUT",
            url: "/api/board/" + id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert("글수정이 완료되었습니다.");
            location.href="/";
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
    
    replySave: function () {
        let data = {
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
            content: $("#reply-content").val()
        }
        
        if(data.content.trim() == '') {
			alert("댓글의 내용을 작성해주세요.");
			$("#boardId").focus();
			return;
		}
		let li = "";
        $.ajax({
            type: "POST",
            url: `/api/board/${data.boardId}/reply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            //alert("댓글 작성이 완료되었습니다.");
            console.log(resp);
            li = "<li id='reply--1' class='list-group-item d-flex justify-content-between'>";
            li += "<div>"+ resp.content +"</div>";
            li += "<div class='d-flex'>" 
            	+ "<div class='font-italic'>작성자 : "+ resp.users.username +"&nbsp;</div>"
            	+ "<div class='font-italic'>작성일시 : "+ resp.createDate +"&nbsp;</div>"
            	+ "<button class='badge'>삭제</button>"
             +"</div>";
            li += "</li>";
            $("#reply--box").prepend(li);
            $("#reply-content").val('');
            //location.href=`/board/${boardId}`;
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
}

index.init();