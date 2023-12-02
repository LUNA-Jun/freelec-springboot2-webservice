var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            /*
                PostApiController에 있는 API에서 이미 @PutMapping으로 선언했기 때문에 PUT을 사용해야 함
                REST에서 CRUD는 다음과 같이 HTTP Method에 매핑됨
                생성(Create)-POST
                읽기(Read)-GET
                수정(Update)-PUT
                삭제(Delete)-DELETE
             */
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();

/*
브라우저의 스코프(scope)는 공용공간으로 쓰이기 때문에 나중에 로딩된 js의 init, save가 먼저 로딩된 js의 function을 덮어쓰게 됨
여러 사람이 참여하는 프로젝트에서는 중복된 함수 이름을 자주 발생할 수 있음
functiron 이름을 확인하면서 만들 수 없음
이러하면 문제를 피하기 위해서 index.js만의 유험범위를 만들어 사용
방법은 var index이란 객체를 만들어 해당 객체에서 필요한 모든 function을 선어하는 것
index 객체 안에서만 function이 유효하기 때문에 다른 js와 겹칠 위험이 사라짐
 */