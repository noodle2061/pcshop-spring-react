package com.id.akn.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Laptop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
	private Brand brand;
	
	@Column(nullable = false, unique = true)
	private String model;

	@Column(name = "status")
	private Short status = 1;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cpu_id")
	private Cpu cpu;
	
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "laptop_gpu", 
               joinColumns = @JoinColumn(name = "laptop_id"),
               inverseJoinColumns = @JoinColumn(name = "gpu_id"))
    private Set<Gpu> gpus = new HashSet<>();

    @Column(name = "ram_memory")
    private byte ramMemory; 
    
	@Column(name = "ram_detail")
    private String ramDetail;

	@Column(name = "disk_capacity")
	private short diskCapacity;
	
	@Column(name = "disk_detail", nullable = false)
	private String diskDetail;
	
	@Column(name = "screen_size")
	private float screenSize;
	
	@Column(name = "screen_detail", nullable = false)
	private String screenDetail;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "os_version_id")
    private OsVersion osVersion;

    @Column(name = "keyboard_type")
    private String keyboardType;
	
	@Column(name = "battery_charger")
	private String batteryCharger;
	
	private String design;

	@OneToMany(mappedBy = "laptop", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<LaptopColor> laptopColors = new HashSet<>();
	
	private String origin;
	
	private byte warranty;

	private Long price;

	@ElementCollection
	@CollectionTable(name = "laptop_images", joinColumns = @JoinColumn(name = "laptop_id"))
	@Column(name = "image_url")
	private Set<String> imageUrls;
	
	@Column(name = "discount_percent")
	private float discountPercent;
		
	@OneToMany(mappedBy="laptop", cascade = CascadeType.ALL, orphanRemoval= true,fetch = FetchType.LAZY )
	private List<Rating> ratings = new ArrayList<>();	
	
	@OneToMany(mappedBy="laptop", cascade = CascadeType.ALL, orphanRemoval= true,fetch = FetchType.LAZY )
	private List<Review> reviews = new ArrayList<>();
	
	@Column(name = "num_ratings")
	private int numRatings;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "laptop_category",
			joinColumns = @JoinColumn(name = "laptop_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();
}