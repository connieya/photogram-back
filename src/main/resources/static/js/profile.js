//  유저 프로파일 페이지 팔로우, 팔로우 취소
function toggleSubscribe(toUserId, obj) {
    if ($(obj).text() === "팔로우 취소") {
        $.ajax({
            type: "delete",
            url: `/api/subscribe/${toUserId}`,
            dataType: "json"
        }).done(res => {
            $(obj).text("팔로우 하기");
            $(obj).toggleClass("blue");
        }).fail(error => {
            console.log("팔로우 취소 실패");
        });
    } else {
        $.ajax({
            type: "post",
            url: `/api/subscribe/${toUserId}`,
            dataType: "json"
        }).done(res => {
            $(obj).text("팔로우 취소");
            $(obj).toggleClass("blue");
        }).fail(error => {
            console.log("팔로우 하기 실패");
        });

    }
}
// 팔로워 모달
function subscribedInfoModalOpen(pageUserId) {
    $(".modal-subscribed").css("display", "flex");

    $.ajax({
        url: `/api/user/${pageUserId}/subscribed`,
        dataType: "json"
    }).done(res => {
        console.log(res)
        res.data.forEach((user) =>{
            let item = getSubscribedModalItem(user)
            $("#subscribedModalList").append(item)
        });
    }).fail(error => {
        console.log(error)
    });
}


// 팔로잉 모달
function subscribeInfoModalOpen(pageUserId) {
    $(".modal-subscribe").css("display", "flex");

    $.ajax({
        url: `/api/user/${pageUserId}/subscribe`,
        dataType: "json"
    }).done(res => {
        console.log(res)
        res.data.forEach((user) =>{
            let item = getSubscribeModalItem(user)
            $("#subscribeModalList").append(item)
        });
    }).fail(error => {
        console.log(error)
    });
}

function getSubscribedModalItem(user) {
    let item =
        `<div class="subscribed__item" id="subscribedModalItem-${user.id}">
         <div class="subscribed__img">
            <img src="/upload/${user.profileImageUrl}" onerror="this.src='/images/person.jpeg'"/>
           </div>
           <div class="subscribed__text">
            <h2>${user.username}</h2>
            </div>
        <div class="subscribed__btn">`;

    if(!user.eqaulUserState){
        if(user.fl){
            item += `<button className="cta blue" onClick="toggleSubscribe(${user.id},this)">팔로우 취소</button>`;
        }else {
            item += `<button className="cta" onClick="toggleSubscribe(${user.id},this)">팔로우 하기</button>`;
        }
    }
    item +=`
        </div>
        </div>`;
    return item;
}


function getSubscribeModalItem(user) {
    let item =
        `<div class="subscribe__item" id="subscribeModalItem-${user.id}">
         <div class="subscribe__img">
            <img src="/upload/${user.profileImageUrl}" onerror="this.src='/images/person.jpeg'"/>
           </div>
           <div class="subscribe__text">
            <h2>${user.username}</h2>
            </div>
        <div class="subscribe__btn">`;

    if(!user.eqaulUserState){
        if(user.subscribeState){
            item += `<button className="cta blue" onClick="toggleSubscribe(${user.id},this)">팔로우 취소</button>`;
        }else {
            item += `<button className="cta" onClick="toggleSubscribe(${user.id},this)">팔로우 하기</button>`;
        }
    }
       item +=`
        </div>
        </div>`;
    return item;
}


function profileImageUpload(pageUserId , principalId) {
    if(pageUserId !== principalId) {
        alert("권한이 없습니다.")
    }

    $("#userProfileImageInput").click();

    $("#userProfileImageInput").on("change", (e) => {
        let f = e.target.files[0];
        if (!f.type.match("image.*")) {
            alert("이미지를 등록해야 합니다.");
            return;
        }

        let profileImageForm = $("#userProfileImageForm")[0];

        //FormData 객체를 이용하면 form 태그의 필드와
        // 그 값을 나타내는 일련의 key/value 쌍을 담을 수 있다.
        let formData = new FormData(profileImageForm);
        $.ajax({
            type: "put",
            url : `/api/user/${principalId}/profileImageUrl`,
            data : formData,
            contentType: false,
            processData : false,
            enctype : "multipart/form-data",
            dataType : "json"
        }).done(res => {
            // 사진 전송 성공시 이미지 변경
            let reader = new FileReader();
            reader.onload = (e) => {
                $("#userProfileImage").attr("src", e.target.result);
            }
            reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
        }).fail(error =>{
            console.log("오류 ",error);
        });
    });
}


// (5) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
    $(obj).css("display", "flex");
}

function closePopup(obj) {
    $(obj).css("display", "none");
}


// (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
    $(".modal-info").css("display", "none");
}

// (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
    $(".modal-image").css("display", "none");
}

// (8) 구독자 정보 모달 닫기
function modalClose() {
    $(".modal-subscribe").css("display", "none");
    location.reload();
}






