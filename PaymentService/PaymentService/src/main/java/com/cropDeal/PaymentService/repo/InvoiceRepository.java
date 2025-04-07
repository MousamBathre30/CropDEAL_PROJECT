package com.cropDeal.PaymentService.repo;





import com.cropDeal.PaymentService.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
