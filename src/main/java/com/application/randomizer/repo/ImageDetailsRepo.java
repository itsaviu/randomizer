package com.application.randomizer.repo;

import com.application.randomizer.domains.ImageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageDetailsRepo extends JpaRepository<ImageDetails, Long> {

    Optional<ImageDetails> findByImageId(Long id);

    void deleteByImageIdIn(List<Long> id);
}
