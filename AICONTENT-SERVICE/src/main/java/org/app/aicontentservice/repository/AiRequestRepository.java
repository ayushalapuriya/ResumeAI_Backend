package org.app.aicontentservice.repository;

import org.app.aicontentservice.entity.AiRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AiRequestRepository extends JpaRepository<AiRequest, String> {

    List<AiRequest> findByUserId(int userId);

    List<AiRequest> findByResumeId(int resumeId);

    Optional<AiRequest> findByRequestId(String requestId);

    List<AiRequest> findByRequestType(String requestType);

    List<AiRequest> findByStatus(String status);

    @Query("SELECT COUNT(a) FROM AiRequest a WHERE a.userId = :userId AND DATE(a.createdAt) = CURRENT_DATE AND a.status = 'COMPLETED'")
    int countByUserIdToday(int userId);

    @Query("SELECT COUNT(a) FROM AiRequest a WHERE a.userId = :userId AND MONTH(a.createdAt) = MONTH(CURRENT_DATE) AND YEAR(a.createdAt) = YEAR(CURRENT_DATE) AND a.status = 'COMPLETED'")
    int countByUserIdThisMonth(int userId);

    @Query("SELECT COALESCE(SUM(a.tokensUsed),0) FROM AiRequest a WHERE a.userId = :userId")
    int sumTokensByUserId(int userId);
}