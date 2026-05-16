package com.example.proj.common;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    public List<DummyPostSeed> getDummyPostSeedList() {
        return List.of(
                new DummyPostSeed(
                        "[분실했어요] 건학기념관 앞 검은색 카드지갑",
                        "건학기념관 앞 계단 근처에서 검은색 카드지갑을 잃어버렸습니다. 안에 학생증이 들어있어요. 발견하신 분은 댓글 부탁드립니다.",
                        "분실물",
                        35.13941187299908f,
                        129.0987035036091f,
                        "/dummyImages/blackWallet.jpeg"
                ),
                new DummyPostSeed(
                        "[습득했어요] 누리생활관 앞 에어팟 케이스",
                        "누리생활관 앞 벤치 옆에서 흰색 에어팟 케이스를 주웠습니다. 본인 확인 후 전달할게요.",
                        "분실물",
                        35.1382926111801f,
                        129.09601516383074f,
                        "/dummyImages/airpods.jpeg"
                ),
                new DummyPostSeed(
                        "제2공학관 벤치 저녁 밥친구",
                        "수업 끝나고 제2공학관 벤치에서 만나서 근처에서 저녁 먹을 사람 구합니다. 메뉴는 만나서 정해요.",
                        "밥친구",
                        35.1433210217013f,
                        129.09559214115183f,
                        "/dummyImages/landscape.jpeg"
                ),
                new DummyPostSeed(
                        "[분실했어요] 학교 후문 파란색 우산",
                        "후문 쪽에서 파란색 장우산을 두고 온 것 같습니다. 비슷한 우산 보신 분 알려주세요.",
                        "분실물",
                        35.14255900936906f,
                        129.0985655614312f,
                        "/dummyImages/blueUmbrella.jpeg"
                ),
                new DummyPostSeed(
                        "도서관 7층 과제 집중 모임",
                        "도서관 7층에서 각자 과제하다가 저녁 같이 먹을 사람 있으면 댓글 주세요. 조용히 집중하다가 밥만 같이 먹는 모임입니다.",
                        "밥친구",
                        35.13899951517195f,
                        129.10055805955656f,
                        "/dummyImages/library.jpeg"
                ),
                new DummyPostSeed(
                        "학생 식당 점심 같이 먹어요",
                        "오늘 점심 학생 식당에서 간단히 먹을 사람 구합니다. 메뉴 보고 바로 이동해요.",
                        "밥친구",
                        35.13841293569732f,
                        129.10000016008144f,
                        "/dummyImages/restaurantMenu.jpeg"
                ),
                new DummyPostSeed(
                        "[습득했어요] 예술관 앞 스케치북",
                        "예술관 입구 근처에서 이름 없는 스케치북을 주웠습니다. 안쪽 그림 설명 가능하신 분께 전달할게요.",
                        "분실물",
                        35.13766090454694f,
                        129.09847973074272f,
                        "/dummyImages/scetchBook.jpeg"
                ),
                new DummyPostSeed(
                        "[분실했어요] 인문관 2층 은색 텀블러",
                        "인문관 2층 강의실 근처에 은색 텀블러를 두고 온 것 같습니다. 보관 중이신 분 계시면 알려주세요.",
                        "분실물",
                        35.141796989902865f,
                        129.09817932333306f,
                        "/dummyImages/silverTumbler.jpeg"
                ),
                new DummyPostSeed(
                        "공대농구장 벤치 운동 후 밥친구",
                        "공대농구장 근처에서 운동 끝나고 밥 먹을 사람 찾습니다. 편하게 합류해주세요.",
                        "밥친구",
                        35.14434872440303f,
                        129.09555535657108f,
                        "/dummyImages/basketballGround.jpeg"
                )
        );
    }

    public record DummyPostSeed(
            String title,
            String content,
            String category,
            Float lat,
            Float lon,
            String imageUrl
    ) {
    }
}
