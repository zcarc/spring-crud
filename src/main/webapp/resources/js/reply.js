/**
 *  reply.js
 */

console.log("reply.js");

var replyService = (function(){

    function insert(reply, callback, error){
        console.log("insert()...");

        $.ajax({
            type: 'post',
            url: "/replies/insert",
            data: JSON.stringify(reply),
            contentType: "application/json; charset=utf-8",
            success : function(result,status,xhr){
                if(callback)
                    callback(result);
            },
            error: function(xhr, status, er){
                if(error)
                    error(er);
            }
        });

    }

    function getListWithPagination(data, callback, error){
        var bno = data.bno;
        var currentPage = data.currentPage || 1;

        $.getJSON("/replies/pages/" + bno + "/" + currentPage + ".json",
            function(result){
                if(callback)
                    callback(result);
            }).fail(function(xhr, status, err){
                if(error && err)
                    error(err);
            });

    }

    function remove(rno, callback, error){
        $.ajax({
            type: 'delete',
            url: "/replies" + "/" + rno,
            success: function(result, status, xhr){
                if(callback)
                    callback(result);
            },
            error: function(xhr, status, err){
                if(error)
                    error(err);
            }

        });
    }

    function update(data, callback, error){
        console.log("rno: " + data.rno);

        $.ajax({
            type:  'put',
            url:  "/replies/" + data.rno,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            success: function(result, status, xhr){
                if(callback)
                    callback(result);
            },
            error: function(xhr,status, err){
                if(error)
                    error(err);
            }

        });
    }

    function read(rno, callback, error){
        $.get("/replies" + "/" + rno + ".json", function(result){
            if(callback)
                callback(result);

        }).fail(function(xhr,status,err){
            if(error)
                error(err);
        });
    }

    function displayTime(timeValue) {

        var today = new Date();

        var gap = today.getTime() - timeValue;

        var dateObj = new Date(timeValue);
        var str = "";


        if(gap < (1000 * 60 * 60 * 24)) {

            var hh = dateObj.getHours();
            var mi = dateObj.getMinutes();
            var ss = dateObj.getSeconds();

            return [ (hh > 9 ? '' : '0') + hh, ':',
                (mi > 9 ? '' : '0') + mi, ':',
                (ss > 9 ? '' : '0') + ss ].join('')

        } else {

            var yy = dateObj.getFullYear();
            var mm = dateObj.getMonth() + 1;
            var dd = dateObj.getDate();

            return [ yy, '/',
                (mm > 9 ? '' : '0') + mm, '/',
                (dd > 9 ? '' : '0') + dd ].join('');
        }


    }

    return {
        insert: insert,
        getListWithPagination: getListWithPagination,
        remove: remove,
        update: update,
        read: read,
        displayTime: displayTime
    };
})();