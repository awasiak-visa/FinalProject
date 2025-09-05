package pl.coderslab.cafe;

import org.springframework.stereotype.Service;

@Service
public class CafeService {
    private CafeRepository cafeRepository;

    public CafeService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }
}
